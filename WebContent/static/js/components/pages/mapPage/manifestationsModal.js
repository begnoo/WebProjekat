Vue.component('manifestations-modal',
{
    template:
    `
	<custom-modal modalName="manifestationsModal" title="Current Manifestations" xl="true">
		<div class="container">
			<div class="row">
				<div class="col">
					<manifestation-card-list v-on:redirection="hideModalBackdrop" :manifestations="manifestations"></manifestation-card-list>
				</div>
			</div>
		</div>
		<div v-show="manifestations.length == 0">
			<h3>No upcoming manifestations this week.</h3>
		</div>
	</custom-modal>
    `,
	
	props: ["manifestations"],

    data: function() {
 		return {
	
		};
    },
    
    methods: {
		hideModalBackdrop: function(){
			$('.modal-backdrop').remove();
		}
    },
		
	mounted: function() {

	}
});