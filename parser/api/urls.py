from django.conf import settings
from django.conf.urls.static import static
from django.contrib import admin
from django.urls import include, path
from drf_spectacular.views import SpectacularAPIView, SpectacularSwaggerView

urlpatterns = []

urlpatterns += [
    path('is/api/parser/__docs__/', SpectacularAPIView.as_view(), name='__docs__'),
    path('api/parser/__docs__/', SpectacularAPIView.as_view()),
    path('api/parser/', SpectacularSwaggerView.as_view(url_name='__docs__')),
    path('api/parser/admin/', admin.site.urls),
    path('api/parser/base/', include('app.base.urls')),
    *static(settings.STATIC_URL, document_root=settings.STATIC_ROOT),
]
