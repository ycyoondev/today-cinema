# Generated by Django 3.2.13 on 2022-05-26 10:33

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('movies', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='movie',
            name='movie_id',
            field=models.IntegerField(default=0),
        ),
    ]
