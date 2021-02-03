Vue.component('comment-form', {
	template:
	`
		<div class="d-flex justify-content-center"">
            <div class="row">
				<form>
					<div class="mb-3">
	        			<label class="form-label">How was your expirience on this manifestation?</label>
	        			<span v-on:click="changeRating(1)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<2" v-on:click="changeRating(2)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=2" v-on:click="changeRating(2)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<3" v-on:click="changeRating(3)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=3" v-on:click="changeRating(3)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<4" v-on:click="changeRating(4)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=4" v-on:click="changeRating(4)" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="rating<5" v-on:click="changeRating(5)" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="rating>=5" v-on:click="changeRating(5)" style="font-size:130%;color:gold;">&starf;</span>

	        			<label for="postCommentText" class="form-label" hidden>Comment</label>
						<textarea v-model="commentText" id="postCommentText" name="commentText" class="form-control" rows="5" cols="100">
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
			commentText: "",
			validators: {
            	'postCommentText': [validateLength(10, 1000)]
            }
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
			
			if(!executeValidation(this.validators)) {
				return;
			}
			
			let loggedUser = localStorage.getObject('loggedUser');
			if(!loggedUser)
			{
				toastr.error(`You must be logged in in order to comment this manifestation.`, '');
				return;
			}
			let userId = loggedUser.user.id;	
			let manifestationId = this.$route.params.id;
			
			axios(postRestConfig('/WebProjekat/rest/comments', {}, 
            {
                'text': this.commentText,
                'rating': this.rating,
                'buyerId': userId,
                'manifestationId': manifestationId
            })
    
            ).then(response => {
				this.clear();
				toastr.success(`You have successfully posted a new comment.`, '');
			 })
             .catch(function(error)
             {
				toastr.error(error.response.data.errorMessage, '');  	
             });
		},
		
		clear: function() {
			this.clearForm();
			clearLastValidation(this.validators);	
		},
		
		clearForm: function() {
			this.rating = 1;
			this.commentText = "";
		}
	}
});