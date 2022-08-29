(function(){
    var grid = document.getElementsByClassName("grid_events")[0];

    var popupEvent = document.getElementsByClassName("popup_event")[0];
    var popupEventButtonClose = popupEvent.getElementsByClassName("popup__button_close")[0];
    var popupEventTitle = popupEvent.getElementsByClassName("popup__title")[0];
    var popupEventDescription = popupEvent.getElementsByClassName("popup__description")[0];
    var popupEventItems = popupEvent.getElementsByClassName("popup__items")[0];

    var popupSpeaker = document.getElementsByClassName("popup_speaker")[0];
    var popupSpeakerButtonClose = popupSpeaker.getElementsByClassName("popup__button_close")[0];
    var popupSpeakerTitle = popupSpeaker.getElementsByClassName("popup__title")[0];
    var popupSpeakerItems = popupSpeaker.getElementsByClassName("popup__items")[0];

    fillGrid();

    function fillGrid() {
        clearGrid();
        sendGetRequest("/events", parseJsonToGridRows);
    }
    function parseJsonToGridRows(events) {
        events.forEach( event => {
            var gridColFirst = document.createElement('td');
            if (new Date(event.date) > new Date()) {
                gridColFirst.className = "grid__col grid__col_first grid__col_highlight";
            } else {
                gridColFirst.className = "grid__col grid__col_first";
            }

            gridColFirst.textContent = event.date;

            var gridColSecond = document.createElement('td');
            gridColSecond.className = "grid__col";

            var descriptionList = document.createElement('dl');
            descriptionList.className = "description";
            var descriptionTerm = document.createElement('dt');
            descriptionTerm.textContent = event.name;
            descriptionTerm.className = "description__term";
            var descriptionDetails = document.createElement('dd');

            if (event.description) {
                descriptionTerm.className = "description__term link link_pseudo";
                descriptionTerm.setAttribute("event_id", event.id);
                descriptionTerm.onclick = function(e) {
                    var eventId = e.target.getAttribute("event_id");
                    sendGetRequest("/event/" + eventId, fillEventPopup);
                }

                var descriptionDetailsText = document.createElement('div');
                descriptionDetailsText.className = "description__details-text";
                descriptionDetailsText.textContent = event.description;
                descriptionDetails.appendChild(descriptionDetailsText);

                descriptionDetails.className = "description__details description__details_collapse";
                descriptionDetails.onclick = function() {
                    if (descriptionDetails.className === "description__details description__details_collapse") {
                        descriptionDetails.className = "description__details description__details_expand";
                    }
                };
            } else {
                descriptionDetails.className = "description__details";
            }


            descriptionList.appendChild(descriptionTerm);
            descriptionList.appendChild(descriptionDetails);
            gridColSecond.appendChild(descriptionList);

            var gridColThird = document.createElement('td');
            gridColThird.className = "grid__col grid__col_last grid__col_text-right";

            var link = document.createElement('a');
            link.className = "link link_margin-left";
            link.textContent = "Original link";
            link.setAttribute("href", event.link);
            link.setAttribute("target", "_blank");

            gridColThird.appendChild(link);

            var gridRow = document.createElement('tr');
            gridRow.className = "grid__body grid__row";
            gridRow.appendChild(gridColFirst);
            gridRow.appendChild(gridColSecond);
            gridRow.appendChild(gridColThird);

            grid.appendChild(gridRow);
        });
    }
    function clearGrid() {
        var gridElements = Array.from(grid.getElementsByClassName("grid__body"));
        gridElements.forEach(gridElement => gridElement.parentNode.removeChild(gridElement));
    }

    function fillSpeakerPopup(speaker) {
        clearSpeakerPopup();

        popupSpeakerTitle.textContent = speaker.name;
        speaker.lectures.forEach( lecture => {
            var listItem = document.createElement('div');
            listItem.className = "list__item";
            var listItemContent = document.createElement('div');
            listItemContent.className = "list__item-content";
            listItemContent.textContent = lecture.theme;

            listItem.appendChild(listItemContent);
            popupSpeakerItems.appendChild(listItem);
        });

        showSpeakerPopup();
    }

    function fillEventPopup(event) {
        clearEventPopup();

        popupEventTitle.textContent = event.name;
        popupEventDescription.textContent = event.description;

        event.lectures.forEach( lecture => {
            var listItem = document.createElement('div');
            listItem.className = "list__item";
            var listItemContent = document.createElement('div');
            listItemContent.className = "list__item-content";
            listItemContent.textContent = lecture.theme;
            var listItemAside = document.createElement('div');
            listItemAside.className = "list__item-aside";
            lecture.speakers.forEach( speaker => {
                var speakerDiv = document.createElement('div');
                speakerDiv.className = "list__item-aside-part link link_pseudo";
                speakerDiv.textContent = speaker.name;
                speakerDiv.setAttribute("speaker_id", speaker.id);
                speakerDiv.onclick = function(e) {
                    var speakerId = e.target.getAttribute("speaker_id");
                    sendGetRequest("/speaker/" + speakerId, fillSpeakerPopup);
                }

                listItemAside.appendChild(speakerDiv);
            });

            listItem.appendChild(listItemContent);
            listItem.appendChild(listItemAside);
            popupEventItems.appendChild(listItem);
        });

        showEventPopup();
    }

    popupEventButtonClose.onclick = function() {
        hideEventPopup();
    }

    function hideEventPopup() {
        popupEvent.classList.add("popup_hide");
    }

    function showEventPopup() {
        popupEvent.classList.remove("popup_hide");
    }

    popupSpeakerButtonClose.onclick = function() {
        hideSpeakerPopup();
    }

    function hideSpeakerPopup() {
        popupSpeaker.classList.add("popup_hide");
    }

    function showSpeakerPopup() {
        popupSpeaker.classList.remove("popup_hide");
    }

    function clearEventPopup() {
        popupEventTitle.textContent = "";
        popupEventDescription.textContent = "";
        popupEventItems.innerHTML = "";
    }

    function clearSpeakerPopup() {
        popupSpeakerTitle.textContent = "";
        popupSpeakerItems.innerHTML = "";
    }


    function sendGetRequest(uri, callback, param) {
        fetch(uri)
        .then(response => response.json())
        .then(json => callback(json, param));
    }

    function sendDeleteRequest(uri, callback, param) {
        fetch(uri, {
            method: 'DELETE'
        })
        .then(json => callback(json, param));
    }

    function sendPostRequest(uri, callback, requestBody) {
        fetch(uri, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: requestBody
        })
        .then(json => callback(json));
    }

    function sendPutRequest(uri, callback, requestBody) {
        fetch(uri, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: requestBody
        })
        .then(json => callback(json));
    }
})();