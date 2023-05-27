from app.base.tests.base import BaseTest
from app.lessons.services.parser import Parser


class XlsxParserTest(BaseTest):
    def test_parse(self):
        parser = Parser()
        lessons = parser.parse()
        print(lessons)
