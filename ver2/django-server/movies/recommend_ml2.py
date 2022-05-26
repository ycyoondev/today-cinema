import pandas as pd 
import warnings; warnings.filterwarnings('ignore')
from django.db.models.functions import Length
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity 
from .models import Movie, Genre


movies = Movie.objects.annotate(alias_length=Length('overview')).filter(alias_length__gt=0)
movies_df = pd.DataFrame(list(movies.values()))
count_vect = CountVectorizer(min_df=0, ngram_range=(1,2)) 
genre_mat = count_vect.fit_transform(movies_df['genres']) 
genre_sim = cosine_similarity(genre_mat, genre_mat) 
genre_sim_sorted_ind = genre_sim.argsort()[:, ::-1]
movies_df[['title', 'tmdb_rating']].sort_values('tmdb_rating', ascending=False)[:10]


def find_recommend_movie(id, top_n=5):
    df = movies_df
    sorted_ind = genre_sim_sorted_ind
    title_movie = df[df['movie_id'] == id]
    title_index = title_movie.index.values
    similar_indexes = sorted_ind[title_index, :(top_n*2)] 
    similar_indexes = similar_indexes.reshape(-1)
    similar_indexes = similar_indexes[similar_indexes != title_index]
    return df.iloc[similar_indexes].sort_values('tmdb_rating', ascending=False)[:top_n]
    

