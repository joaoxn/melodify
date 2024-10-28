export interface Performer {
    id: string;
    name: string;
    artists: {
        name: string;
        performerRole: string;
    }[]
}
