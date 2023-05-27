from collections import defaultdict
import re

from app.lessons.entites.cell import CellEntity
from app.lessons.models import Lesson
from app.lessons.services.converters.cell import CellConverter
from app.lessons.services.fixer import CellFixer
from app.lessons.services.xlsx_parser import XlsxParser


def crop_array(array):
    cropped_array = []
    for row in array:
        cropped_row = row  # Обрезаем каждую строку до 4 элементов
        cropped_array.append(cropped_row)
    return cropped_array


class Parser:
    def __init__(self):
        self.xlsx_parser = XlsxParser()
        self.cell_converter = CellConverter()
        self.fixer = CellFixer()
        self.lesson_manager = Lesson.objects

    def parse(self, filename: str = 'schedule.xlsx'):
        data = self.xlsx_parser.parse(filename)
        data = crop_array(data)  # FIXME: to simplify
        course_map = self._map_courses(data[0])
        group_map = self._map_groups(data[1])
        indexed_groups = self._index_groups(course_map, group_map)
        for row, row_list in enumerate(data):
            if row > 3:
                cells = []
                for column, cell_text in enumerate(row_list):
                    if column > 1:
                        if cell_text:
                            try:
                                cell = self._create_cell(
                                    cell_text, row, column, course_map, group_map
                                )
                            except ValueError:
                                continue
                            cells.append(cell)
                print(row, '-' * 10)
                cells = self.fixer.fix(cells)
                lessons = self.cell_converter.convert_to_lessons(cells, indexed_groups)

    def _extract_course_number(self, course: str) -> int:
        match = re.search(r'(\d+)\s+курс', course.strip())
        if match:
            return int(match.group(1))
        raise ValueError

    def _map_courses(self, courses_row: list[str]) -> dict[int, int]:
        course_map = {}
        for column, course in enumerate(courses_row):
            if column > 1:
                course_map[column] = self._extract_course_number(course)
        return course_map

    def _map_groups(self, groups_row: list[str]) -> dict[int, tuple[str, int]]:
        group_map = {}
        current_group = None
        current_subgroup = None
        for column, group in enumerate(groups_row):
            if column > 1:
                group = group.strip()
                if group == current_group:
                    current_subgroup += 1
                else:
                    current_group = group
                    current_subgroup = 1
                group_map[column] = current_group, current_subgroup
        return group_map

    def _index_groups(
        self, course_map: dict[int, int], group_map: dict[int, tuple[str, int]]
    ) -> dict[int, dict[str, int]]:
        indexed_groups: dict[int, dict[str, int]] = defaultdict(dict)
        current_course = 1
        indexed_groups_on_course: dict[str, int] = defaultdict(int)
        for column, (group, subgroup) in group_map.items():
            course = course_map[column]
            if indexed_groups_on_course[group] < subgroup:
                indexed_groups_on_course[group] = subgroup
            if current_course != course:
                indexed_groups[current_course] = indexed_groups_on_course
                current_course = course
                indexed_groups_on_course = defaultdict(int)
                indexed_groups_on_course[group] = subgroup
        indexed_groups[current_course] = indexed_groups_on_course
        return indexed_groups

    def _create_cell(
        self, cell_text: str, row, column, course_map, group_map
    ) -> CellEntity:
        cell_data: dict = self._parse_cell_text(cell_text)
        group, subgroup = group_map[column]
        cell_data['group'] = group
        cell_data['subgroup'] = subgroup
        cell_data['is_denominator'] = self._get_is_denominator(row)
        cell_data['course'] = course_map[column]
        cell_data['weekday'] = self._get_weekday(row)
        cell_data['number'] = self._get_number(row)
        return CellEntity(**cell_data)

    def _get_weekday(self, row: int) -> int:
        intervals = {
            (4, 19): 0,
            (21, 36): 1,
            (38, 53): 2,
            (55, 70): 3,
            (72, 87): 4,
            (89, 102): 5,
        }
        for interval, weekday in intervals.items():
            if interval[0] <= row <= interval[1]:
                return weekday
        raise ValueError

    def _get_number(self, row: int) -> int:
        row -= 4
        if 98 < row < 0 or row + 1 % 17 == 0:
            raise ValueError
        skips = row // 16
        result = (row - skips) // 2
        return result % 8

    def _get_is_denominator(self, row: int) -> bool:
        return bool(row % 2)

    def _parse_cell_text(self, cell_text: str) -> dict[str, str]:
        cell_text = re.sub(r'(\s|\n_){2,}', ' ', cell_text)
        if len(cell_text.split(' ')) < 5:
            raise ValueError
        cell_text, placement = cell_text.rsplit(' ', 1)
        cell_text, _, surname, initials = cell_text.rsplit(' ', 3)
        employee_name = f"{surname} {initials}"
        name = cell_text
        return {
            'employee_name': employee_name,
            'name': name,
            'placement': placement,
        }
