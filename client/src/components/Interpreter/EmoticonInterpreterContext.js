import { createContext } from "react";
import dictionary from "../../file/emoticonsDictionary.json";

export const EmoticonInterpreterContext = createContext(undefined);

export function EmoticonInterpreter({ children }) {
  const emojiInterpret = (text) => {
    const wordTable = text.split(" ");

    return wordTable
      .map((x) => {
        const emoji = dictionary[x];
        return !!emoji ? emoji : x;
      })
      .join(" ");
  };

  return (
    <EmoticonInterpreterContext.Provider value={{ emojiInterpret }}>
      {children}
    </EmoticonInterpreterContext.Provider>
  );
}
