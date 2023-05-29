from drf_spectacular.types import OpenApiTypes
import re
from rest_framework import serializers
from drf_spectacular.utils import extend_schema_field

from app.base.serializers.base import BaseModelSerializer, BaseSerializer
from app.employees.models import Employee
from app.lessons.models import Lesson


class _GET_Lessons_Weekday_LessonSerializer(BaseModelSerializer):
    class Meta:
        model = Lesson
        read_only_fields = ['id', 'name', 'groups', 'placement']


class _GET_Lessons_WeekdaySerializer(BaseSerializer):
    time = serializers.CharField()
    lessons = serializers.ListSerializer(
        child=_GET_Lessons_Weekday_LessonSerializer(many=True)
    )


class _GET_Lessons_EmployeesSerializer(BaseModelSerializer):
    class Meta:
        model = Employee
        read_only_fields = ['id', 'name']


class GET_LessonsSerializer(BaseSerializer):
    employees = _GET_Lessons_EmployeesSerializer(many=True)
    monday = _GET_Lessons_WeekdaySerializer(many=True)
    tuesday = _GET_Lessons_WeekdaySerializer(many=True)
    wednesday = _GET_Lessons_WeekdaySerializer(many=True)
    thursday = _GET_Lessons_WeekdaySerializer(many=True)
    friday = _GET_Lessons_WeekdaySerializer(many=True)
    saturday = _GET_Lessons_WeekdaySerializer(many=True)

    TIMES = [
        '8:00 - 9:35',
        '9:45 - 11:20',
        '11:30 - 13:05',
        '13:25 - 15:00',
        '15:10 - 16:45',
        '16:55 - 18:30',
        '18:40 - 20:00',
        '20:10 - 21:30',
    ]
    WEEKDAYS = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday']

    def to_representation(self, instance):
        instance |= self._get_schedule()
        return super().to_representation(instance)

    def _get_schedule(self) -> dict[str, list[dict[str, str | list[list[Lesson]]]]]:
        employees: list[Employee] = self.instance['employees']
        lessons = Lesson.objects.index_lessons(Lesson.objects.all())
        schedule = {}
        for weekday, weekday_name in enumerate(self.WEEKDAYS):
            schedule_by_weekday = []
            for number, time_name in enumerate(self.TIMES):
                lessons_by_number = []
                for employee in employees:
                    lessons_by_employee = []
                    for is_denominator in False, True:
                        lesson = lessons[(employee, weekday, number, is_denominator)]
                        lessons_by_employee.append(lesson)
                    lessons_by_number.append(lessons_by_employee)
                schedule_by_number = {'time': time_name, 'lessons': lessons_by_number}
                schedule_by_weekday.append(schedule_by_number)
            schedule[weekday_name] = schedule_by_weekday
        return schedule
