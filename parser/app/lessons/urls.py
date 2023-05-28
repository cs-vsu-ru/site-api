from django.urls import path

from app.lessons.views import (
    LessonsParseView,
    LessonsView,
    LessonsByEmployeeView,
    LessonView,
)

urlpatterns = [
    path('', LessonsView.as_view()),
    path('<int:id>/', LessonView.as_view()),
    path('by_employee/<int:employee_id>/', LessonsByEmployeeView.as_view()),
    path('parse/', LessonsParseView.as_view()),
]
