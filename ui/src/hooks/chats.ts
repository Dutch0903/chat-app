import { useEffect, useState } from "react";
import { ChatType, getChats, type Chat } from "../api";

export const useChats = () => {
    const [chats, setChats] = useState<Chat[]>([]);

    const fetchChats = () => {
        getChats().then((chatResponse) => {
            setChats(chatResponse.data);
        });
    };

    useEffect(() => {
        fetchChats();
    }, []);


    const hasPrivateChatBetween = (
        participants: string[]
    ) => chats.filter((chat) => {
        if (chat.type != ChatType.PRIVATE) {
            return false;
        }

        return JSON.stringify(participants.sort()) == JSON.stringify(chat.participants.sort());
    }).length > 0;

    return {
        chats,
        hasPrivateChatBetween,
        fetchChats
    };
}