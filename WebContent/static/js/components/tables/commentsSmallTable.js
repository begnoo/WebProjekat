Vue.component('comments-small-table',
{
    template:
    `
    	<div>
	    	<div class="modal fade" id="commentModal" tabindex="-1" role="dialog" v-bind:aria-hidden=true>
				<div class="modal-dialog modal-lg" role="document">
				    <div class="modal-content">
				    	<div class="modal-header">
				    		<h5 class="modal-title" id="commentModalLabel">Comment</h5>
				    		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				    			<span aria-hidden="true">&times;</span>
				    		</button>
				    	</div>
						<div class="modal-body">
							<div class="card-body">
								<div class="container">
								  <div class="row">
								  
								  	<label for="commentText" style="font-size:130%;" class="form-label">By: {{ selectedComment.buyer.name}}&nbsp;&nbsp;</label>
				        			<span style="font-size:130%;color:gold;">&starf;</span>
									<span v-if="selectedComment.rating<2" style="font-size:130%;color:gold;">&star;</span>
									<span v-if="selectedComment.rating>=2" style="font-size:130%;color:gold;">&starf;</span>
									<span v-if="selectedComment.rating<3" style="font-size:130%;color:gold;">&star;</span>
									<span v-if="selectedComment.rating>=3" style="font-size:130%;color:gold;">&starf;</span>
									<span v-if="selectedComment.rating<4" style="font-size:130%;color:gold;">&star;</span>
									<span v-if="selectedComment.rating>=4" style="font-size:130%;color:gold;">&starf;</span>
									<span v-if="selectedComment.rating<5" style="font-size:130%;color:gold;">&star;</span>
									<span v-if="selectedComment.rating>=5" style="font-size:130%;color:gold;">&starf;</span>
								  	
								  	<textarea style="background-color:white;"name="commentText" class="form-control" rows="5" cols="100" disabled>{{ selectedComment.text }} </textarea>
								  </div>
								</div>
							 </div>
						</div>
				    	<div class="modal-footer">
	    				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				    	</div>
					</div>
				</div>
			</div>
		
	    	<div class="container">
		        <table class="table">
					<thead class="thead-dark">
						  <tr>
								<th scope="col">Buyer</th>
								<th scope="col">Manifestation</th>
								<th scope="col">Rating</th>
								<th scope="col">Text</th>
								<th scope="col">Status</th>
								<th scope=""></th>
						  </tr>
					</thead>
					<tbody>
						  <tr
						  	v-for="(comment, index) in comments"
						  	v-on:click="selectComment(comment)"
						  	v-bind:class="{ 'table-active' : selectedComment === comment}"
						  >
								<td>{{ comment.buyer.name }}</td>
								<td>{{ comment.manifestation.name }}</td>
								<td>{{ comment.rating }}</td>
								<td>{{ getShortenText(comment.text) }}</td>
								<td>{{ comment.status }}</td>
								<td>
									<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#commentModal">
										View
									</button>
									
									<template v-if="userRole=='Seller' && comment.status=='Pending'">
										<button type="button" class="btn btn-success btn-sm" v-on:click="changeStatusTo(comment.id, 'Approved')">
											&#10003
										</button>
										
										<button type="button" class="btn btn-danger btn-sm" v-on:click="changeStatusTo(comment.id, 'Refused')">
											&#10799
										</button>
									</template>
    							</td>
						  </tr>
					</tbody>
		      </table>	      
	    	</div>
	    </div>
    `,

	props: ["comments", "selectedPage", "pageSize"],

    data: function() {
        return {
            selectedComment: {
            	rating: 0,
            	text: "",
            	buyer: {
            		name: ""
        	},
            userRole: null
            }
        }
    },
    
    methods: {
    
    	selectComment: function(comment) {
    		this.selectedComment = comment;
		},
		
		getShortenText: function(text) {
			if(text.length >= 70) {
				return text.substring(0, 60) + "...";
			}
			
			return text;
		},
		
		changeStatusTo(commentId, status){
			 axios(putRestConfig('/WebProjekat/rest/comments/' + commentId + '/status', {status: status}, {}))
	            .then(response => {
	            	alert("Uspesno");
	            	this.selectedComment.status = status;
	            })
	            .catch(function(error)
				{
					alert(error.response.data.errorMessage);  	
				});
		}
    },
		
	mounted: function() {
		let loggedUser = localStorage.getObject('loggedUser');
		this.userRole = loggedUser.user.role;
	}
});