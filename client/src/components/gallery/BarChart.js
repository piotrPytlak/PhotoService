import {Bar} from 'react-chartjs-2';
import React, {Component, useContext, useEffect, useRef, useState} from "react";
import Chart from 'chart.js/auto'
import {galleryContext} from "../../store/GalleryContext";
import {useParams} from "react-router-dom";
import "./styleGallery.css"


export function BarChart() {

    const {photoInformation} = useContext(galleryContext)
    const bar = useRef();

    const averages = [photoInformation.creativityAvg || 0, photoInformation.lightingAvg || 0, photoInformation.originalityAvg || 0,
        photoInformation.qualityAvg || 0, photoInformation.artisticImpressionsAvg || 0]

    const categories = ["Creativity", "Lighting", "Originality", "Quality", "Artistic Impressions"]


    const [charData, setCharData] = useState({
        labels: categories,
        datasets: [
            {
                label: 'Averages',
                data: [],
                backgroundColor: [
                    'rgba(53, 162, 235, 0.5)',

                ]
            }
        ]
    })

    const options = {
        plugins:{
            legend: {
                display: false
            },
        },

        responsive: true,

        scales: {
            y: {
                max: 5,
                min: 0,
            },

        }

    };

    useEffect(() => {
        bar.current?.data.datasets.forEach((dataset) => {
            dataset.data = []
            averages.forEach(x => dataset.data.push(x));
        });
        bar.current?.update();

        return () => {
            bar.current?.data.datasets.forEach((dataset) => {
                dataset.data = []

            });
            bar.current?.update();
        }
    }, [photoInformation])
    return (
        <div style={{position: "relative", padding: "auto", margin: "auto", width: "75%"}}>
            <h1 style={{textAlign: 'center'}}>Rate by category</h1>
            <Bar options={options} data={charData} ref={bar}/>
        </div>
    )
}

//className={'chart'}
