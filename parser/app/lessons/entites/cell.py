from app.base.entities.base import BaseEntity


class CellEntity(BaseEntity):
    employee_name: str
    name: str
    placement: str
    course: int
    group: str
    subgroup: int
    is_denominator: bool
