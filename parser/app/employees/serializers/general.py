from rest_framework import serializers

from app.base.serializers.base import BaseSerializer


class POST_EmployeesSerializer(BaseSerializer):
    employee_id = serializers.IntegerField()
