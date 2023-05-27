from app.base.tests.base import BaseTest
from app.employees.tests.factories.employee import EmployeeFactory
from app.lessons.models import Lesson
from app.lessons.services.parser import Parser


class XlsxParserTest(BaseTest):
    def test_parse(self):
        EmployeeFactory(name='Ермаков М.В.')
        parser = Parser()
        lessons = parser.parse()
        Lesson.objects.bulk_create(lessons)
        self.assert_true(Lesson.objects.count(), 27)
