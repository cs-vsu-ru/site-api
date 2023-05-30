from app.base.views.base import BaseView
from app.employees.serializers.general import POST_EmployeesSerializer
from app.employees.services.synker import EmployeeSynker


class EmployeesView(BaseView):
    serializer_map = {'post': (201, POST_EmployeesSerializer)}

    def post(self):
        serializer = self.get_valid_serializer()
        synker = EmployeeSynker()
        synker.synk_by_id(serializer.validated_data['employee_id'])
