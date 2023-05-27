from collections import defaultdict

from app.lessons.entites.cell import CellEntity
from app.lessons.models import Lesson
from app.lessons.services.converters.group import GroupConverter


class CellConverter:
    def __init__(self):
        self.group_converter = GroupConverter()

    def convert_to_lessons(
        self, cells: list[CellEntity], indexed_groups: dict[int, dict[str, int]]
    ) -> list[Lesson]:
        lessons: list[Lesson] = []
        employee_cells_map: dict[str, list[CellEntity]] = defaultdict(list)
        for cell in cells:
            employee_cells_map[cell.employee_name].append(cell)
        for employee, employee_cells in employee_cells_map.items():
            group_text = self.group_converter.convert(employee_cells, indexed_groups)
            # TODO: lessons
        return lessons
