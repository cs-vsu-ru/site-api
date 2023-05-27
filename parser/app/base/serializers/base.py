from drf_spectacular.utils import OpenApiResponse
from rest_framework import serializers
from rest_framework.validators import UniqueValidator


class BaseSerializer(serializers.Serializer):
    WARNINGS: dict = {}
    _DESCRIPTION = None

    @classmethod
    def get_schema(cls, success_status: int = 200) -> dict[int, OpenApiResponse]:
        if cls._DESCRIPTION is None:
            description = None
        else:
            description = '\t' + cls._DESCRIPTION
        return {success_status: OpenApiResponse(cls, description=description)} | {
            warning.status: warning.get_schema() for warning in cls.WARNINGS.values()
        }

    def is_valid(self, raise_exception=True):
        return super().is_valid(raise_exception=raise_exception)

    def update(self, instance, validated_data):
        pass

    def create(self, validated_data):
        pass


class BaseModelSerializer(serializers.ModelSerializer, BaseSerializer):
    Meta: type

    def is_valid(self, raise_exception=True):
        return super().is_valid(raise_exception)

    def get_extra_kwargs(self):
        extra_kwargs = super().get_extra_kwargs()
        write_only_fields = getattr(self.Meta, 'write_only_fields', None)
        if write_only_fields is not None:
            if not isinstance(write_only_fields, (list, tuple)):
                raise TypeError(
                    "The `write_only_fields` option must be a list or tuple. "
                    "Got %s." % type(write_only_fields).__name__
                )
            for field_name in write_only_fields:
                kwargs = extra_kwargs.get(field_name, {})
                kwargs['write_only'] = True
                extra_kwargs[field_name] = kwargs
        return extra_kwargs

    def get_field_names(self, declared_fields, info):
        fields = getattr(self.Meta, 'fields', [])
        if write_only_fields := getattr(self.Meta, 'write_only_fields', []):
            fields += write_only_fields
        if read_only_fields := getattr(self.Meta, 'read_only_fields', []):
            fields += read_only_fields
        setattr(self.Meta, 'fields', fields)
        return super().get_field_names(declared_fields, info)

    def build_standard_field(self, field_name, model_field):
        field_class, field_kwargs = super().build_standard_field(
            field_name, model_field
        )
        field_kwargs['validators'] = list(
            filter(
                lambda validator: not isinstance(validator, UniqueValidator),
                field_kwargs.get('validators', []),
            )
        )
        return field_class, field_kwargs
