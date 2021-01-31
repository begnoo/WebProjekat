Vue.component('comments-page',
	{
		template:
			`
	<div class="content">
		<div class="row justify-content-center mt-5">
			<div v-show="comments.length == 0">
				<h3>There are no comments for this manifestation.</h3>
			</div>
			<div v-if="isSellerAndManifestationOwner()">
				<div v-show="comments.length != 0">
					<comments-small-table :comments="comments"></comments-small-table>
			    	<pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setComments"></pagination>
				</div>
			</div>
			<div v-else>
				<div v-show="comments.length != 0">
					<comments-big-table :comments="comments"></comments-big-table>
				    <pagination :trigger="trigger" :restConfig="restConfig" :pageSize="pageSize" :selectedPage="selectedPage" v-on:update-page-data="setComments"></pagination>
					<comment-form v-if="isBuyer()"></comment-form>
				</div>
			</div>
		</div>
  	</div>
    `,

		data: function() {
			return {
				comments: [],
				restPath: "../WebProjekat/rest/manifestations/",
				restConfig: null,
				pageSize: 5,
				selectedPage: 1,
				trigger: false,
				manifestationId: null,
			}
		},

		methods: {

			setComments: function({ emittedData, selectedPage }) {
				this.comments = emittedData
				this.selectedPage = selectedPage
			},
			isSellerAndManifestationOwner: function() {
				return localStorage.isLoggedUserRole(["Seller"]) && this.isSellersManifestation(this.manifestationId);
			},
			isSellersManifestation: function() {
				const loggedUser = localStorage.getObject("loggedUser");
				return loggedUser.user.manifestations.find(manifestation => manifestation.id == this.manifestationId) != undefined;
			},
			isBuyer: function(){
				return localStorage.isLoggedUserRole(["Buyer"]);
			}
		},

		created: function() {
			this.manifestationId = this.$route.params.id;
			this.restPath += this.manifestationId + "/comments";
			if (!this.isSellerAndManifestationOwner()) {
				this.restPath += "/approved";
			}
			this.restConfig = getRestConfig(this.restPath);
		}

	});