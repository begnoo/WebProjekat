Vue.component("search-manifestations-form", {
	template: `
    <div class="container mt-3">
		<div class = "row justify-content-md-center">
		<form>
			<div class="form-row">
		    	<div class="col">
		      		<input v-model="name" type="text" class="form-control" id="inputName" placeholder="Name">
				</div>
				<div class="form-group row">
				    <div class="col-sm-10">
					    <div class="input-group">	
							<div class="btn-group" role="group" aria-label="Basic example">
								<button v-on:click="this.submitSearch" type="submit" class="btn btn-primary">Search</button>
								<button v-on:click="this.showHideForm" class="btn btn-primary dropdown-toggle"></button>
							</div>
						</div>
				    </div>
		    	</div>
		  	</div>
			<div v-show="showForm">
				<div class="form-row">
					<div class="col-12">
						<input v-model="city" type="text" class="form-control" id="inputCity" placeholder="City">
				    </div>
				</div>
				
				<div class="form-row mt-3">
					<label for="fromPriceField" class="col-sm-2 col-form-label">Price:</label>
					<div class="col-5">
						<input v-model="priceFrom" type="number" min="0" max="Math.pow(2,31) - 1" class="form-control" id="fromPriceField" placeholder="From">
				    </div>
					<div class="col-5">
					    <input v-model="priceTo" type="number" min="0" max="Math.pow(2,31) - 1" class="form-control" id="toPriceField" placeholder="To">
				    </div>
				</div>
				
				
				<div class="form-row mt-3">
					<label for="dateFromField" class="col-sm-3 col-form-label">Date from:</label>
					<div class="col-5">
						<input v-model="dateFrom" type="date" class="form-control" id="dateFromField" placeholder="From">
				    </div>
					<div class="col-4">
						<input type="time" class="form-control" id="eventEndTimeField" v-model="timeFrom">
					</div>
				</div>
				
				<div class="form-row mt-3">
					<label for="dateToField" class="col-sm-3 col-form-label">Date to:</label>
					<div class="col-5">
					    <input v-model="dateTo" type="date" class="form-control" id="dateToField" placeholder="To">
				    </div>
					<div class="col-4">
						<input type="time" class="form-control" id="eventEndTimeField" v-model="timeTo">
					</div>
				</div>
		
				<div class="form-row mt-3">
				    <label for="selectRole" class="col-sm-2 col-form-label">Type:</label>
				    <div class="col-10">
						<select v-model="type" class="form-control" id="selectType">
							<option value="">All</option>
							<option value="Concert">Concert</option>
							<option value="Festival">Festival</option>
							<option value="Theater">Theater</option>
						</select>
				    </div>
			 	</div>
			
				<div class="form-row mt-3">
					<div class="col-4">
						<label class="form-check-label" for="defaultCheck1">
						   Only not sold out: 
						 </label>
					</div>

				    <div class="col-auto">

						<input v-model="onlyNotSolved" class="form-check-input" type="checkbox" value="" id="defaultCheck1">
				    </div>
				 </div>
		
				<div class="form-row mt-3 mb-3">
				    <label for="selectSort" class="col-sm-2 col-form-label">Sort by:</label>
				    <div class="col-5">
						<select v-model="sortBy" class="form-control" id="selectSort">
							<option value="">None</option>
							<option value="name">Name</option>
							<option value="eventDate">Date</option>
							<option value="regularTicketPrice">Price</option>
							<option value="regularTicketPrice">Location</option>
						</select>
				    </div>
				    <div class="col-5">
						<select v-model="orderBy" class="form-control" id="selectOrder">
							<option value="Ascending">Ascending</option>
							<option value="Descending">Descending</option>
						</select>
				    </div>
			 	</div>
			</div>
		</form>
		</div>
	</div>
    `,


	data: function() {
		return {
			name: "",
			type: "",
			city: "",
			priceFrom: null,
			priceTo: null,
			onlyNotSolved: false,
			dateTo: null,
			timeTo: null,
			dateFrom: null,
			timeFrom: null,
			sortBy: "",
			orderBy: "Ascending",
			showForm: false,
		}
	},

	methods: {
		submitSearch: function() {
			this.$emit("search-manifestation-data", {
				name: this.name,
				surename: this.surename,
				type: this.type,
				city: this.city,
				priceFrom: (this.priceFrom) ? this.priceFrom : 0,
				priceTo: (this.priceTo) ? this.priceTo : Math.pow(2,31) - 1,
				onlyNotSolved: this.onlyNotSolved,
				dateFrom: this.getDate(this.dateFrom, this.timeFrom),
				dateTo: this.getDate(this.dateTo, this.timeTo),
				sortBy: this.sortBy,
				orderBy: this.orderBy,
			});
		},
		showHideForm: function(){
			this.showForm = !this.showForm;
		},
		getDate: function(date, time){
			if(!date){
				return null;
			}
			if(!time){
				return date + " 00:00";
			}
			return date + " " + time;
		}
	},

	created: function() {

	},

});
