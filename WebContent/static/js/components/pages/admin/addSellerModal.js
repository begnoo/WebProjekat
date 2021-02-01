Vue.component("add-seller-modal", {
    template: `
	    <custom-modal modalName="addSellerModal" title="Create Seller">
			<registration-form 
				v-on:create-user-success="handleSuccess" 
				userType="seller">
			</registration-form>
	    </custom-modal>
    `,
	
    data: function () {
        return {
        };
    },

    methods: {
        handleSuccess: function(){
			this.$emit('create-user-success');
			$('#addSellerModal').modal('hide');
		},
    },
});
