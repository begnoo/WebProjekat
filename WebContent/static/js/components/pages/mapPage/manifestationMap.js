Vue.component("manifestation-map", {
    template: `
    <div ref="map-root" style="width: 100%; height: 900px;">
    </div>
    `,
    data: function () {
        return {
            map: null,
            markerFeatures: [],
        };
    },

    props: ["locations", "zoom", "initTrigger"],

	watch: {
		"initTrigger": function(){
			this.initMap();
		}
	},
	

    methods: {
		addMarkers: function(){
			for(let location of this.locations){
				let feature =  new ol.Feature({
	                geometry: new ol.geom.Point(ol.proj.fromLonLat([location.longitude, location.latitude])),
	            });

				feature.setStyle(new ol.style.Style({
			    image: new ol.style.Icon({
				  scale: 0.15,
			      src: '../WebProjekat/rest/images/marker.png',
			    })}));
				
				feature.setProperties({
					...feature.getProperties(),
					locationId: location.id,
				});
				
				this.markerFeatures.push(feature);
			}
		},
        initMap: function () {
			
			this.addMarkers();
			
			vectorLayer = new ol.layer.Vector({
				source: new ol.source.Vector({
					features: this.markerFeatures,
						wrapX: true,
		            }),
					wrapX: false,
	            });

			const startingCoordinates = this.locations.length != 0 ? [this.locations[0].longitude, this.locations[0].latitude] : [45.28, 19.83];

            this.map = new ol.Map({
                target: this.$refs["map-root"],
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM({
							wrapX: false,
						}),
                    }),
                    vectorLayer,
                ],

                view: new ol.View({
                    zoom: this.zoom,
                    center: ol.proj.transform(
	                    startingCoordinates,
	                    "EPSG:4326",
	                    "EPSG:3857"
               		),
                    constrainResolution: true,
                }),
            });

			this.map.on('click', (event) => {
				let clickedFeature = this.map.forEachFeatureAtPixel(event.pixel, (feature) => {
				    return feature;
				});
				if(clickedFeature){
					this.$emit("location-clicked", clickedFeature.getProperties().locationId);
				}
			});
        },

    },

    mounted() {
        //this.initMap();
    },
});
