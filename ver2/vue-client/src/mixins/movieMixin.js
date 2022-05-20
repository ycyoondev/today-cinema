export default {
  methods : {
    setToken: function (token) {
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
		loginCheck : function(token) {
			return token
		},
		errorMessage : function(res) {
			var message = ""
			for (let key in res.data) {
				message = message+ res.data[key] +"\n"
			}
			return message
		}
  },
  filters : {
    imageURL : function (movie) {
      // console.log(movie)
      return "https://image.tmdb.org/t/p/w500"+movie.poster_path
    },
    timeFor : function(created_at){
			// console.log(created_at)
			const today = new Date();
			const timeValue = new Date(created_at);

			const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
			if (betweenTime < 1) return '방금전';
			if (betweenTime < 60) {
					return `${betweenTime}분전`;
			}

			const betweenTimeHour = Math.floor(betweenTime / 60);
			if (betweenTimeHour < 24) {
					return `${betweenTimeHour}시간전`;
			}

			const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
			if (betweenTimeDay < 365) {
					return `${betweenTimeDay}일전`;
			}

			return `${Math.floor(betweenTimeDay / 365)}년전`;
		}
  }
}