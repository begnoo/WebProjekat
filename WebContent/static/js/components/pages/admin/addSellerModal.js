Vue.component("add-seller-modal", {
    template: `
	    <custom-modal modalName="addSellerModal" title="Create Seller">
			<registration-form 
				v-on:seller-submit="openSellerConfirmation"
				v-on:create-user-success="handleSuccess" 
				userType="seller">
			</registration-form>
			<template v-slot:inner-modal>
				<confirmation-modal
					v-on:closed="clearLastValidationWrapper"
					type="primary"
					modalName="confirmationAddSeller" 
					title="Confirm Add" 
					:callback="createSeller"
					callbackData="">
					Are you sure you want to add this seller?
				</confirmation-modal>
			</template>
	    </custom-modal>
    `,
	
    data: function () {
        return {
			clearLastValidationWrapper: function(){},
			createSeller: function(){},
        };
    },

    methods: {
		openSellerConfirmation: function(sellerCallback, clearCallback){
			this.createSeller = sellerCallback;
			this.clearLastValidationWrapper = clearCallback;
			$("#confirmationAddSeller").attr("style", "z-index: 1055; background: rgba(0, 0, 0, 0.3);");
			$("#confirmationAddSeller").modal("toggle");
		},
        handleSuccess: function(){
			this.$emit('create-user-success');
			$('#addSellerModal').modal('toggle');
			$("#confirmationAddSeller").modal("toggle");
		},
    },
});
