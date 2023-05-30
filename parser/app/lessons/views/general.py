from django.utils.decorators import method_decorator
from django.views.decorators.cache import cache_page
from rest_framework.response import Response

from app.base.views.base import BaseView
from app.employees.models import Employee
from app.lessons.serializers.general import GET_LessonsSerializer


class LessonsView(BaseView):
    serializer_map = {'get': GET_LessonsSerializer}
    queryset = Employee.objects.order_by('name')
    pagination_class = None

    @method_decorator(cache_page(60))
    def get(self):
        serializer = self.get_serializer({'employees': self.get_filtered_queryset()})
        return Response(serializer.data)
