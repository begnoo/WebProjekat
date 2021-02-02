Vue.component("manifestation-image-form", {
	template: `
	<div>
	    <div class="form-group">
	        <label for="manifestationImageFile">Manifestation image: </label>
	        <input type="file" class="form-control-file" id="manifestationImageFile" v-on:change="getImage">
	    </div>
	    <div v-show="loading">
	        Loading image...
	    </div>
	</div>

    `,

	props: ["manifestationId", "trigger"],

	data: function() {
		return {
			image: null,
			loading: false,
		};
	},

	watch: {
		"manifestationId": function() {
			this.addManifestationImage();
		},
		"trigger": function() {
			this.addManifestationImage();
		}
	},

	methods: {
		getImage: function(event) {
			this.encodeImage(event.target.files[0]);
		},
		encodeImage: function(input) {
			if (input) {
				const reader = new FileReader();
				reader.onload = (event) => {
					this.loading = true;
					this.image = event.target.result;
					this.loading = false;
				};
				reader.onerror = function (error) {
					console.log('Error: ', error);
				};
				reader.readAsDataURL(input);
			}
		},
		addManifestationImage: function() {
			if(this.image){
				this.loading = true;
				axios(putRestConfig("/WebProjekat/rest/images", {},
				{
					base64Representation: this.image,
					manifestationId: this.manifestationId,
				}))
				.then((response) => {
					this.$emit("update-success", response.data);
					this.loading = false;
				})
				.catch(function(error) {
					toastr.error(error.response.data.errorMessage, '');
					this.loading = false;
				});
			}
		},
	},
});
