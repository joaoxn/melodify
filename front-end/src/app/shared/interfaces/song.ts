export interface Song {
    id: string;
    name: string;
    performerId?: string;
    genreIds?: string[];
    views: number;
    src: string;
    fileType: string;
}
