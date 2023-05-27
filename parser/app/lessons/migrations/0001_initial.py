# Generated by Django 4.2.1 on 2023-05-27 13:43

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('employees', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Lesson',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('weekday', models.SmallIntegerField(db_index=True)),
                ('number', models.SmallIntegerField(db_index=True)),
                ('is_denominator', models.BooleanField()),
                ('name', models.TextField()),
                ('groups', models.TextField()),
                ('placement', models.TextField()),
                ('employee', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='lessons', to='employees.employee')),
            ],
            options={
                'ordering': ['id'],
                'abstract': False,
            },
        ),
    ]
