from django.contrib import admin
from django.urls import include, path
from drf_spectacular.views import SpectacularAPIView, SpectacularSwaggerView

urlpatterns = [
    path('__docs__/', SpectacularAPIView.as_view(), name='__docs__'),
    path('', SpectacularSwaggerView.as_view(url_name='__docs__')),
    path('admin/', admin.site.urls),
    path('base/', include('app.base.urls')),
]
