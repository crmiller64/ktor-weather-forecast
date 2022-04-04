import { useEffect, useState } from "react";

import addFormValidation from "./utils/addFormValidation";
import states from "./utils/usStates"

const Location = props => {
    const [city, setCity] = useState("");
    const [state, setState] = useState("");

    const handleSubmit = event => {
        event.preventDefault();
        props.onSubmit(city, state);
    }

    const stateOptions = states.map((state, index) =>
        <option key={state.abbreviation} value={state.abbreviation}>
            {state.name}
        </option>
    );

    useEffect(() => {
        addFormValidation();
    });

    return (
        <div className="card shadow">
            <div className="card-body">
                <h5 className="card-title">Location</h5>
                <form className="validated-form" onSubmit={handleSubmit} noValidate>
                    <div className="mb-3">
                        <label htmlFor="city" className="form-label">City</label>
                        <input
                            className="form-control"
                            type="text"
                            id="city"
                            name="city"
                            value={city}
                            required
                            onChange={event => setCity(event.target.value)}
                        />
                        <div className="invalid-feedback">
                            A city is required.
                        </div>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="state" className="form-label">State</label>
                        <input
                            className="form-control"
                            type="text"
                            id="state"
                            name="state"
                            value={state}
                            required
                            onChange={event => setState(event.target.value)}
                        />
                        <div className="invalid-feedback">
                            A state is required.
                        </div>
                    </div>
                    <div className="mb-3">
                        <button className="btn btn-primary">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Location;