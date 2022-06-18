# Generated by Django 3.2.13 on 2022-05-26 13:02

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('movies', '0002_movie_movie_id'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='movie',
            name='genre_id',
        ),
        migrations.RemoveField(
            model_name='movie',
            name='genre_name',
        ),
        migrations.AddField(
            model_name='movie',
            name='genres',
            field=models.CharField(default='', max_length=100),
            preserve_default=False,
        ),
    ]