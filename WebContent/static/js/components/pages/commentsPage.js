Vue.component('comments-page',
{
    template:
    `
	<div class="content">
		<div class="row justify-content-center mt-5">
			<div v-show="comments.length == 0">
				<h3>There are no comments for this manifestation.</h3>
			</div>
			<div v-if="showAll()">
				<div v-show="comments.length != 0">
					<comments-small-table :comments="comments"></comments-small-table>
			    	<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setComments"></pagination>
				</div>
			</div>
			<div v-else>
				<comments-big-table :comments="comments"></comments-big-table>
			    <pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setComments"></pagination>
				<comment-form></comment-form>
			</div>
		</div>
  	</div>
    `,

    data: function() {
        return {
            comments: [],
			restPath : "../WebProjekat/rest/manifestations/",
			restConfig: null,
			pageSize: 5,
			selectedPage : 1,
			trigger: false,
        }
    },
    
    methods: {
		
		setComments: function({emittedData, selectedPage}){
			this.comments = emittedData
			this.selectedPage = selectedPage
		},
		showAll: function(){
			console.log("koji k")
			return localStorage.isLoggedUserRole(["Seller", "Administrator"]);
		}
    },

	created: function(){
		let manifestationId = this.$route.params.id;
		this.restPath += manifestationId + "/comments";
		if(localStorage.isLoggedUserRole(["Buyer", ""])){
			this.restPath += "/approved";
		}
		this.restConfig = getRestConfig(this.restPath);
	}
		
});