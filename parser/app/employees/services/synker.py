import requests

from app.employees.models import Employee
from app.lessons.models import Lesson


class EmployeeSynker:
    def __init__(self):
        self.url = 'http://api:8080/api/employees'
        self.requester = requests
        self.employee_manager = Employee.objects
        self.lessons_manager = Lesson.objects

    def _update_or_create_employee(self, id: int, name: str) -> Employee:
        try:
            employee = self.employee_manager.get(id=id)
            employee.name = name
            employee.save()
        except self.employee_manager.model.DoesNotExist:
            employee = self.employee_manager.create(id=id, name=name)
        return employee

    def synk(self) -> None:
        self.employee_manager.all().delete()
        employees_data = self.requester.get(self.url).json()
        for employee_data in employees_data:
            name = self._parse_name(employee_data)
            self._update_or_create_employee(employee_data['id'], name)

    def synk_by_id(self, id: int) -> None:
        employees_data = self.requester.get(self.url).json()
        for employee_data in employees_data:
            if employee_data['id'] == id:
                name = self._parse_name(employee_data)
                employee = self._update_or_create_employee(id, name)
                self.lessons_manager.create_for_employee(employee)
                return
        raise KeyError

    def _parse_name(self, employee_data) -> str:
        first_name = employee_data['firstName']
        last_name = employee_data['lastName']
        patronymic = employee_data['patronymic']
        return f"{last_name} {first_name[0]}.{patronymic[0]}."
