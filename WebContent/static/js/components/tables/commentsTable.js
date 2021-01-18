
Vue.component("comments-table", {
    template: `
	<div class="container">
		<div v-for="comment in comments">
			<div class="card mb-2" style="background-color:#f5f5f5;">
				<div class="card-body">
					<div class="container">
					  <div class="row">
					  
					  	<label for="commentText" style="font-size:130%;" class="form-label">By: {{comment.buyer.name}}&nbsp;&nbsp;</label>
	        			<span style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="comment.rating<2" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="comment.rating>=2" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="comment.rating<3" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="comment.rating>=3" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="comment.rating<4" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="comment.rating>=4" style="font-size:130%;color:gold;">&starf;</span>
						<span v-if="comment.rating<5" style="font-size:130%;color:gold;">&star;</span>
						<span v-if="comment.rating>=5" style="font-size:130%;color:gold;">&starf;</span>
					  	
					  	<textarea style="background-color:white;"name="commentText" class="form-control" rows="5" cols="100" disabled>{{ comment.text }} </textarea>
					  </div>
					</div>
				 </div>
			</div>
		</div>
	</div>
	`,
    data: function () {
        return {
            comments: [],
        };
    },

    methods: {
        getCommentsForManifestation: function(){
			let manifestationId = this.$route.params.id;
        
			axios.get("/WebProjekat/rest/manifestations/" + manifestationId + "/comments", 
			{
				params: {
					"status" : "Approved"
				}
			}
			).then(response => {
				this.comments = response.data;
			})
			.catch(error => alert(error));
		}
		
		
    },

	mounted: function(){
		this.getCommentsForManifestation();
	}
});
