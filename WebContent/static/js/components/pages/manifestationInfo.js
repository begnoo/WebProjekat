Vue.component("manifestation-info", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
			{{manifestation}}
            </div>
        </div>
    </div>
    `,
	props: ['manifestation'],
	
	mounted: function(){
		console.log("hej pa tu sam valjda negde kao");
		console.log(manifestation);
	},

});
