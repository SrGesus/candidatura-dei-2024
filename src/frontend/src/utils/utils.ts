

export type align = "end" | "center" | "start" | undefined

export function getGradeColor(grade: number, weight: number): string {
  if (grade === undefined) {
    return 'grey';
  }
  if (grade <= weight / 3) {
    return 'error';
  } else if (grade <= 2 * weight / 3) {
    return 'warning';
  } else {
    return 'success';
  }
}

// Typescript can't seem to infer `item.${string}` so this function is needed
// as i can't cast inside the v-slot directive key
export function getKey(id: number): `item.${string}` {
  return `item.${id}`;
}

export const fuzzySearch = (value: string, search: string): boolean => {
  // Regex to match any character in between the search characters
  const searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
