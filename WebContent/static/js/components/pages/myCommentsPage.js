Vue.component("my-comments-page", {
	template: `
    <div class="container">
		<div v-show="comments.length != 0" class="row">
			<div>
	            <div class="col-10 mt-3">
	                <comments-big-table :comments="comments"></comments-big-table>
	            </div>
	        </div>
	        <div class="row">
				<div class="col-10">
					<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" v-on:update-page-data="setComments"></pagination>
	            </div>
	        </div>
		</div>
        <div v-show="comments.length == 0" class="mt-5">
            <h3 style="text-align:center">You haven't made any comments yet.</h3>
        </div>
    </div>
    `,

	data: function() {
		return {
			comments: [],
			pageSize: 3,
			selectedPage: 1,
			restConfig: null,
			restPath: "/WebProjekat/rest/users/",
			trigger: false,
		};
	},

	methods: {
		setComments: function({emittedData, selectedPage}){
			this.comments = emittedData;
			this.selectedPage = selectedPage;
		}
	},

	created: function() {
		let buyerId = localStorage.getObject("loggedUser").user.id;
		this.restPath += `${buyerId}/comments`;
		this.restConfig = getRestConfig(this.restPath);
	}
});
