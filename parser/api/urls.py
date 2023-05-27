from django.conf import settings
from django.contrib import admin
from django.urls import include, path
from drf_spectacular.views import SpectacularAPIView, SpectacularSwaggerView

urlpatterns = []

urlpatterns += [
    path('is/__docs__/', SpectacularAPIView.as_view(), name='__docs__'),
    path('', SpectacularSwaggerView.as_view(url_name='__docs__')),
    path('admin/', admin.site.urls),
    path('base/', include('app.base.urls')),
]

if settings.URL_PREFIX is not None:
    urlpatterns = [path(f"{settings.URL_PREFIX}/", include(urlpatterns))]
