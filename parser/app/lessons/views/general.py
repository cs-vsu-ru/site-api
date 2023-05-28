from app.base.views.base import BaseView
from app.lessons.models import Lesson
from app.lessons.serializers.general import GET_LessonsSerializer


class LessonsView(BaseView):
    many = True
    serializer_map = {'get': GET_LessonsSerializer}
    queryset = Lesson.objects.all()
    
    def get(self):
        return self.list()
