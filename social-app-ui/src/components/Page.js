import React, {useEffect} from 'react';
import Container from './Container';

function Page(props){

    useEffect(() => {
        document.title = `${props.title} | ComplexApp`;
        window.scrollTo(0, 0);//scroll page ontop every time
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.title]);

    return(
        <Container>
            {props.children}
        </Container>
    );
}
export default Page;