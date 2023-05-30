from django.utils.decorators import method_decorator
from django.views.decorators.cache import cache_page

from app.base.views.base import BaseView
from app.employees.models import Employee
from app.lessons.serializers.by_employee import GET_LessonsByEmployeeSerializer


class LessonsByEmployeeView(BaseView):
    serializer_map = {'get': GET_LessonsByEmployeeSerializer}
    queryset = Employee.objects.all()
    lookup_field = 'id'
    lookup_url_kwarg = 'employee_id'

    @method_decorator(cache_page(60))
    def get(self):
        return self.retrieve()
