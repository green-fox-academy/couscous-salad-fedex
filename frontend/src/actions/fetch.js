export async function fetchAction(url, options, success, error) {
  try {
    const response = await fetch(url, options);
    const responseBody = await response.json();

    if (response.status !== 200) {
      throw Error(responseBody.error);
    }
    success(responseBody);
  } catch (err) {
    error(err);
  }
};
