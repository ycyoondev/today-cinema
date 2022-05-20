import pandas as pd 
import warnings; warnings.filterwarnings('ignore')
from ast import literal_eval 
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity 


movies = pd.read_csv('movies/data/tmdb_5000_movies.csv', encoding='utf-8') 
movies_df = movies[['id', 'title', 'genres', 'vote_average', 'vote_count', 'popularity', 'keywords', 'overview']] 
movies_df['genres'] = movies_df['genres'].apply(literal_eval) 
movies_df['genres'] = movies_df['genres'].apply(lambda x:[y['name'] for y in x]) 
movies_df['genres_literal'] = movies_df['genres'].apply(lambda x:(' ').join(x)) 
count_vect = CountVectorizer(min_df=0, ngram_range=(1,2)) 
genre_mat = count_vect.fit_transform(movies_df['genres_literal']) 
genre_sim = cosine_similarity(genre_mat, genre_mat) 
genre_sim_sorted_ind = genre_sim.argsort()[:, ::-1]
percentile = 0.6
m = movies['vote_count'].quantile(percentile)
C = movies['vote_average'].mean()

def weighted_vote_average(record): 
    v = record['vote_count'] 
    R = record['vote_average']
    return ((v/(v+m))*R) + ((m/(m+v))*C)


movies_df['weighted_vote'] = movies_df.apply(weighted_vote_average, axis=1)
movies_df[['title', 'vote_average', 'weighted_vote', 'vote_count']].sort_values('weighted_vote', ascending=False)[:10]

def find_sim_movie(id, top_n=5):
    df = movies_df
    sorted_ind = genre_sim_sorted_ind
    title_movie = df[df['id'] == id]
    title_index = title_movie.index.values
    similar_indexes = sorted_ind[title_index, :(top_n*2)] 
    similar_indexes = similar_indexes.reshape(-1)
    similar_indexes = similar_indexes[similar_indexes != title_index]
    return df.iloc[similar_indexes].sort_values('weighted_vote', ascending=False)[:top_n]
