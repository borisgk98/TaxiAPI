-- возращает растояние между географическими координатами в метрах
CREATE OR REPLACE FUNCTION distance_delta(
    latFrom double precision,
    longFrom double precision,
    latTo double precision,
    longTo double precision
)
    RETURNS double precision AS
$BODY$
BEGIN
    return ST_Distance(
            ST_PointFromText('POINT(' || longFrom || ' ' || latFrom || ')', 4326),
            ST_PointFromText('POINT(' || longTo || ' ' || latTo || ')', 4326),
            true
        );
END;
$BODY$
    LANGUAGE plpgsql;

-- прогнозируемое дополнительное время, нужно пользователю, чтобы воспользоваться этой поездкой относительно идеальной поездки
CREATE OR REPLACE FUNCTION compute_trip_rate(
    latFrom double precision,
    longFrom double precision,
    latTo double precision,
    longTo double precision,
    tripLatFrom double precision,
    tripLongFrom double precision,
    tripLatTo double precision,
    tripLongTo double precision,
    targetTime timestamp,
    tripTime timestamp
)
    RETURNS double precision AS
$BODY$
DECLARE
--     distance delta in km
    startDelta double precision;
--     distance delta in km
    finishDelta double precision;
--     delta time in seconds
    timeDelta double precision;
BEGIN
    startDelta := distance_delta(latFrom, longFrom, tripLatFrom, tripLongFrom);
    finishDelta := distance_delta(latTo, longTo, tripLatTo, tripLongTo);
    timeDelta := cast(extract(epoch from (targetTime - tripTime)) as double precision);
    return (startDelta + finishDelta) * 1000 / (5.0 / 60.0 / 60.0) * 2 + timeDelta;
END;
$BODY$
    LANGUAGE plpgsql;
    
    
-- прогнозируемое дополнительное время, нужно пользователю, чтобы воспользоваться этой поездкой относительно идеальной поездки (неполный поиск)
CREATE OR REPLACE FUNCTION compute_trip_rate(
    latFrom double precision,
    longFrom double precision,
    tripLatFrom double precision,
    tripLongFrom double precision,
    targetTime timestamp,
    tripTime timestamp
)
    RETURNS double precision AS
$BODY$
DECLARE
--     distance delta in km
    startDelta double precision;
--     delta time in seconds
    timeDelta double precision;
BEGIN
    startDelta := distance_delta(latFrom, longFrom, tripLatFrom, tripLongFrom);
    timeDelta := cast(extract(epoch from (targetTime - tripTime)) as double precision);
    return startDelta * 1000 / (5.0 / 60.0 / 60.0) * 2 + timeDelta;
END;
$BODY$
    LANGUAGE plpgsql;