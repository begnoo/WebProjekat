Vue.component('root-page',
{
    template:
    `
	<div>
		<slot></slot>
	</div>
    `,

	props: ["modalName", "title"],

    data: function() {
 		return {
	
		};
    },
    
    methods: {
    
    },
		
	mounted: function() {

	}
});