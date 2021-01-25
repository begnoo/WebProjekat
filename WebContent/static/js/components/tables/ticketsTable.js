Vue.component("tickets-table", {
	template: `
    <div class="container">
        <div class="row">
            <div class="col mt-3">
				<table class="table">
				  <thead class="thead-dark">
				    <tr>
				      <th scope="col">Manifestation</th>
				      <th scope="col">Type</th>
				      <th scope="col">Price(RSD)</th>
					  <th scope="col">Status</th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr v-for="ticket in tickets" :key="ticket.id">
				      <th><router-link :to="'/manifestations/' + ticket.manifestation.id">{{ticket.manifestation.name}}</router-link></th>
				      <td>{{ticket.type}}</td>
				      <td>{{ticket.price}}</td>
				      <td>{{ticket.status}}</td>
				    </tr>
				  </tbody>
				</table>
            </div>
        </div>
    </div>
    `,

	props: ["tickets"],

	methods: {

	},

});
