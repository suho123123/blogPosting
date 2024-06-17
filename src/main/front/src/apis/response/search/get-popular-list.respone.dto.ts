import ResponseDto from "../response.dto";

export default interface GetPopularListResponeDto extends ResponseDto {
    popularWordList: string[];
}