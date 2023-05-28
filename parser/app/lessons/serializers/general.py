from app.base.serializers.base import BaseModelSerializer
from app.lessons.models import Lesson


class GET_LessonsSerializer(BaseModelSerializer):
    class Meta:
        model = Lesson
        read_only_fields = [
            'id',
            'employee',
            'weekday',
            'number',
            'is_denominator',
            'name',
            'groups',
            'placement',
        ]
