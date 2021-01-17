Vue.component("manifestation-image-form", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-6">
                <form v-on:submit="addManifestationImage">
                <div class="form-group">
                    <label for="manifestationImageFile">Manifestation image: </label>
                    <input type="file" class="form-control-file" id="manifestationImageFile" v-on:change="getImage">
                </div>
                <div v-show="loading">
                    Loading image...
                </div>
                <div class="form-group">
                    <div class="d-flex d-flex justify-content-between">
                        <button class="btn btn-primary" :diabled="loading">Add Manifestation Image</button>
                    </div>
                </div>
                </form>
            </div>
            <div class="col">
            </div>
        </div>
    </div>
    `,
    data: function () {
        return {
            manifestationId: null,
            image: null,
            loading: false,
        };
    },

    methods: {
        getImage: function (event) {
            this.encodeImage(event.target.files[0]);
        },
        encodeImage: function (input) {
            if (input) {
                const reader = new FileReader();
                reader.onload = (event) => {
                    this.loading = true;
                    this.image = event.target.result;
                    this.loading = false;
                };
                reader.readAsDataURL(input);
            }
        },
        addManifestationImage: function (event) {
            event.preventDefault();
            console.log(this.image);
            this.loading = true;
            axios
                .post("/WebProjekat/rest/images", {
                    base64Representation: this.image,
                    manifestationId: this.manifestationId,
                })
                .then((response) => {
                    alert("Uspesno");
                    console.log(response);
                    this.loading = false;
                })
                .catch(function (error) {
                    alert(error.response.data.errorMessage);
                    this.loading = false;
                });
        },
    },
});
