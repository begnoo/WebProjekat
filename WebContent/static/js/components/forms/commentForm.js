Vue.component('comment-form', {
	template:
	`
		<div class="d-flex justify-content-center min-vh-100 align-items-center">
            <div class="row">
			
				<form>
					<div class="mb-3">
	        			<label for="commentText" class="form-label">How was your expirience on this manifestation?</label>
	        			<span v-on:click="changeRating(1)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<2" v-on:click="changeRating(2)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=2" v-on:click="changeRating(2)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<3" v-on:click="changeRating(3)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=3" v-on:click="changeRating(3)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<4" v-on:click="changeRating(4)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=4" v-on:click="changeRating(4)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<5" v-on:click="changeRating(5)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=5" v-on:click="changeRating(5)" style="font-size:130%;color:gold;">&starf;</span>

						<textarea v-model="commentText" id="commentText" name="commentText" class="form-control" rows="5" cols="100">
						</textarea>
					</div>
										
					<div class="mb-3" >
						<button v-on:click="sendComment" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	`,
	
	data: function() {
		return {
			rating: 1,
			commentText: ""
		}
	},
	
	methods:
	{
		changeRating: function(changedRate)
		{
			this.rating = changedRate;
		},
		
		sendComment: function(event)
		{
			event.preventDefault();
			let loggedUser = localStorage.getObject('loggedUser');
			if(!loggedUser)
			{
				alert("You must be logged in in order to comment this manifestation.");
				return;
			}
			let userId = loggedUser.user.id;	
			let manifestationId = this.$route.params.id;
			
			axios.post('/WebProjekat/rest/comments', 
            {
                'text': this.commentText,
                'rating': this.rating,
                'buyerId': userId,
                'manifestationId': manifestationId
            }
    
            ).then(response => alert("Uspesno") )
             .catch(function(error)
              {
                alert(error.response.data.errorMessage);  	
              });
		}
	}
});