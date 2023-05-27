import requests

from app.employees.models import Employee


class EmployeeSynker:
    def __init__(self):
        self.url = 'http://api:8080/api/employees'
        self.requester = requests
        self.employee_manager = Employee.objects

    def synk(self) -> None:
        employees_data = self.requester.get(self.url).json()
        for employee_data in employees_data:
            name = self._parse_name(employee_data)
            self.employee_manager.create(id=employee_data['id'], name=name)

    def _parse_name(self, employee_data) -> str:
        first_name = employee_data['firstName']
        last_name = employee_data['lastName']
        patronymic = employee_data['patronymic']
        return f"{last_name} {first_name[0]}.{patronymic[0]}."
