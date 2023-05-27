from django.db import models

from app.base.models.base import BaseModel
from app.employees.models import Employee


class Lesson(BaseModel):
    employee = models.ForeignKey(Employee, models.CASCADE, related_name='lessons')
    weekday = models.SmallIntegerField(db_index=True)
    number = models.SmallIntegerField(db_index=True)
    name = models.TextField()
    course = models.TextField()
    group = models.TextField()
