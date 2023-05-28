from app.base.views.base import BaseView
from app.employees.models import Employee
from app.lessons.serializers.by_employee import GET_LessonsByEmployeeSerializer


class LessonsByEmployeeView(BaseView):
    serializer_map = {'get': GET_LessonsByEmployeeSerializer}
    queryset = Employee.objects.all()
    lookup_field = 'id'
    lookup_url_kwarg = 'employee_id'
    
    def get(self):
        return self.retrieve()
